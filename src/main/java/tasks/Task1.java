package tasks;

import common.Person;
import common.PersonService;

import java.util.*;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);

    // O(n) по времени, O(n) по памяти
    Map<Integer, Person> personMap = new HashMap<>();
    for (Person person : persons) {
      personMap.put(person.id(), person);
      System.out.println(person);
    }

    // Восстанавливаем порядок согласно personIds
    // O(m) по времени, где m - размер personIds
    List<Person> result = new ArrayList<>();
    for (Integer id : personIds) {
      Person person = personMap.get(id);
    }

    return result;
  }
}
