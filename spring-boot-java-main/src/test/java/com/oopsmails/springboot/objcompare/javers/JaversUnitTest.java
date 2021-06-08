package com.oopsmails.springboot.objcompare.javers;

import com.oopsmails.springboot.objcompare.Address;
import com.oopsmails.springboot.objcompare.Person;
import com.oopsmails.springboot.objcompare.PersonWithAddress;
import lombok.extern.slf4j.Slf4j;
import org.javers.common.collections.Lists;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.container.ListChange;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class JaversUnitTest {

    @Test
    public void givenPersonObject_whenApplyModificationOnIt_thenShouldDetectChange() {
        // given
        Javers javers = JaversBuilder.javers().build();

        Person person = new Person(1, "Michael Program");
        Person personAfterModification = new Person(1, "Michael Java");

        // when
        Diff diff = javers.compare(person, personAfterModification);

        // then
        ValueChange change = diff.getChangesByType(ValueChange.class).get(0);
        log.info("### -> ValueChange change = {}", change);

        assertThat(diff.getChanges().size(), equalTo(1));
        assertThat(change.getPropertyName(), equalTo("name"));
        assertThat(change.getLeft(), equalTo("Michael Program"));
        assertThat(change.getRight(), equalTo("Michael Java"));
    }

    @Test
    public void givenListOfPersons_whenCompare_ThenShouldDetectChanges() {
        final String oldV = "Michael Program";
        final String newV = "Michael Not Program";

        // given
        Javers javers = JaversBuilder.javers().build();
        Person personThatWillBeRemoved = new Person(2, "Thomas Link");
        List<Person> oldList = Lists.asList(new Person(1, oldV), personThatWillBeRemoved);
        List<Person> newList = Lists.asList(new Person(1, newV));

        // when
        Diff diff = javers.compareCollections(oldList, newList, Person.class);

        // then
        assertThat(diff.getChanges().size(), equalTo(3));

        ValueChange valueChange = diff.getChangesByType(ValueChange.class).get(0);
        log.info("### -> ValueChange valueChange = {}", valueChange);

        assertThat(valueChange.getPropertyName(), equalTo("name"));
        assertThat(valueChange.getLeft(), equalTo(oldV));
        assertThat(valueChange.getRight(), equalTo(newV));

        ObjectRemoved objectRemoved = diff.getChangesByType(ObjectRemoved.class).get(0);
        assertThat(objectRemoved.getAffectedObject().get().equals(personThatWillBeRemoved), equalTo(true));

        ListChange listChange = diff.getChangesByType(ListChange.class).get(0);
        assertThat(listChange.getValueRemovedChanges().size(), equalTo(1));
    }

    @Test
    public void givenListOfPerson_whenPersonHasNewAddress_thenDetectThatChange() {
        // given
        Javers javers = JaversBuilder.javers().build();

        PersonWithAddress person = new PersonWithAddress(1, "Tom", Arrays.asList(new Address("England")));

        PersonWithAddress personWithNewAddress = new PersonWithAddress(1, "Tom", Arrays.asList(new Address("England"), new Address("USA")));

        // when
        Diff diff = javers.compare(person, personWithNewAddress);
        List objectsByChangeType = diff.getObjectsByChangeType(NewObject.class);

        // then
        assertThat(objectsByChangeType.size(), equalTo(1));
        assertThat(objectsByChangeType.get(0), equalTo(new Address("USA")));
    }

    @Test
    public void givenListOfPerson_whenPersonRemovedAddress_thenDetectThatChange() {
        // given
        Javers javers = JaversBuilder.javers().build();

        PersonWithAddress person = new PersonWithAddress(1, "Tom", Arrays.asList(new Address("England")));

        PersonWithAddress personWithNewAddress = new PersonWithAddress(1, "Tom", Collections.emptyList());

        // when
        Diff diff = javers.compare(person, personWithNewAddress);
        List objectsByChangeType = diff.getObjectsByChangeType(ObjectRemoved.class);

        // then
        assertThat(objectsByChangeType.size(), equalTo(1));
        assertThat(objectsByChangeType.get(0), equalTo(new Address("England")));
    }
}
