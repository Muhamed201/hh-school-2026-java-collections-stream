package tasks;

import common.Person;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {

  public static List<Person> sort(Collection<Person> persons) {
    //Как я понял прошлый код ломается, когда один из
    // критериев сортировки(secondName, firstName, createdAt) вернул null
    //теперь такие объекты будут перемещены в конец списка
//    return persons.stream()
//            .sorted(Comparator.comparing(Person::secondName, Comparator.nullsLast(Comparator.naturalOrder()))
//                    .thenComparing(Person::firstName, Comparator.nullsLast(Comparator.naturalOrder()))
//                    .thenComparing(Person::createdAt, Comparator.nullsLast(Comparator.naturalOrder())))
//            .collect(Collectors.toList());
    //Или можно так
    //Убираем всех, у кого фамилия, имя или дата создания — null
    return persons.stream()

            .filter(p -> p.secondName() != null &&
                    p.firstName() != null &&
                    p.createdAt() != null)
            .sorted(Comparator.comparing(Person::secondName)
                    .thenComparing(Person::firstName)
                    .thenComparing(Person::createdAt))
            .collect(Collectors.toList());
    //оба варианта рабочие
  }
}
