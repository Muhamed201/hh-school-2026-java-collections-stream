package tasks;

import common.Area;
import common.Person;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    // Создаем map для быстрого поиска региона по его id
    Map<Integer, Area> areaById = areas.stream()
            .collect(Collectors.toMap(Area::getId, area -> area));

    // Проходим по всем персонам, для каждой получаем id регионов,
    // затем для каждого региона формируем строку и собираем в Set
    return persons.stream()
            .flatMap(person -> {
              // Получаем id регионов для данной персоны (может быть null)
              Set<Integer> regionIds = personAreaIds.get(person.id());
              if (regionIds == null || regionIds.isEmpty()) {
                return java.util.stream.Stream.empty();
              }
              // Для каждого id региона ищем соответствующий Area и формируем строку
              return regionIds.stream()
                      .map(regionId -> {
                        Area area = areaById.get(regionId);
                        if (area == null) {
                          return null;
                        }
                        return person.firstName() + " - " + area.getName();
                      })
                      .filter(java.util.Objects::nonNull); // отбрасываем null
            })
            .collect(Collectors.toSet());
  }
}

