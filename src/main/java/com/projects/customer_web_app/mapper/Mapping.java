package com.projects.customer_web_app.mapper;

import java.util.List;

public interface Mapping<F, T> {
    T map(F from);

    default T update(F from, T to) {
        return to;
    }

    default List<T> map(List<F> fromList) {
        return fromList.parallelStream()
                .map(this::map)
                .toList();
    }
}