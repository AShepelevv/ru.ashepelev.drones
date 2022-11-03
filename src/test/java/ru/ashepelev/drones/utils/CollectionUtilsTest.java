package ru.ashepelev.drones.utils;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilsTest {
    @Test
    void map() {
        var list = List.of(1, 2, 3, 4, 5);
        assertThat(CollectionUtils.map(list, i -> i * i)).asList().containsExactly(1, 4, 9, 16, 25);
        assertThat(list).asList().containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void mapToSet() {
        var list = List.of(1, 2, 3, 4, 5);
        assertThat(CollectionUtils.mapToSet(list, i -> i * i)).containsExactlyInAnyOrder(1, 4, 9, 16, 25);
        assertThat(CollectionUtils.mapToSet(list, i -> i * i)).isInstanceOf(Set.class);
        assertThat(list).asList().containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void filterAndMap() {
        var list = List.of(1, 2, 3, 4, 5);
        assertThat(CollectionUtils.filterAndMap(list, i -> i % 2 == 0, i -> i * i))
                .asList().containsExactly(4, 16);
        assertThat(list).asList().containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void toMap() {
        var list = List.of(1, 2, 3, 4, 5);
        Map<Character, Integer> expected = Map.of(
                'a', 1,
                'b', 4,
                'c', 9,
                'd', 16,
                'e', 25
        );
        assertThat(CollectionUtils.toMap(list, i -> (char) ('a' + i - 1), i -> i * i)).isEqualTo(expected);
        assertThat(list).asList().containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void toKeyMap() {
        var list = List.of(1, 2, 3, 4, 5);
        Map<Character, Integer> expected = Map.of(
                'a', 1,
                'b', 2,
                'c', 3,
                'd', 4,
                'e', 5
        );
        assertThat(CollectionUtils.toKeyMap(list, i -> (char) ('a' + i - 1))).isEqualTo(expected);
        assertThat(list).asList().containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void minus() {
        var minuend = List.of(1, 1, 2, 2, 3, 4, 5);
        var subtrahend = List.of(2, 3, 4);
        assertThat(CollectionUtils.minus(minuend, subtrahend)).asList().containsExactly(1, 1, 5);
    }
}
