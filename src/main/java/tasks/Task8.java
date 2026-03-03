package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;
import java.util.stream.Collectors;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

    public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {
        // Собираем все personId
        Set<Integer> personIds = persons.stream()
                .map(Person::id)
                .collect(Collectors.toSet());
        // Получаем все резюме для этих personId
        Set<Resume> resumes = personService.findResumes(personIds);
        // Группируем резюме по personId, собирая в Set
        Map<Integer, Set<Resume>> resumesByPersonId = resumes.stream()
                .collect(Collectors.groupingBy(
                        Resume::personId,
                        Collectors.toSet()
                ));
        // Для каждой персоны создаём PersonWithResumes
        return persons.stream()
                .map(person -> {
                    Set<Resume> personResumes = resumesByPersonId.getOrDefault(person.id(), Collections.emptySet());
                    return new PersonWithResumes(person, personResumes);
                })
                .collect(Collectors.toSet());
    }
}
