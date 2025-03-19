package com.boluo.core.optional;

import com.boluo.utils.Animal;

import java.util.Optional;

/**
 * @author chao
 * @datetime 2025-03-15 20:58
 * @description 封装工具类判空
 */
public class NullSafe {
    public static <T> Optional<T> ofNullable(T value) {
        return Optional.ofNullable(value);
    }

    public static void main(String[] args) {
        Animal animal = new Animal();
        String str = NullSafe.ofNullable(animal)
                .map(Animal::getStr1)
                .orElse("default value");
        System.out.println(str);
    }
}
