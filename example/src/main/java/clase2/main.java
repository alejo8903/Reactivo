package clase2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class main {
    public static void main(String[] args) {
        List<Persona> personas = Arrays.asList(
                new Persona("Juan", 25, "M"),
                new Persona("Maria", 30, "F"),
                new Persona("Pedro", 35, "M"),
                new Persona("Ana", 20, "F")
        );

        System.out.println(personas.stream()
                .filter(persona -> persona.getEdad() > 25 )
                .count());

        personas.stream().map(persona ->
                persona.getNombre())
                .collect(Collectors.toList())
                .forEach(name -> System.out.println(name));

        System.out.println(personas.stream()
                .map(persona -> persona.getEdad())
                .reduce(0,(Integer::sum)));
    }
}
