package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    // Так нам не придеться менять исходный список
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    //Здесь distinct(), так как Collectors.toSet() уже выдаёт уникальные элементы
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toSet());
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    String result = "";
    if (person.secondName() != null) {
      result += person.secondName();
    }

    if (person.firstName() != null) {
      result += " " + person.firstName();
    }

    if (person.middleName() != null) { // было дублирование secondName
      result += " " + person.middleName();
    }
    return result;
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> map = new HashMap<>(persons.size()); // указали реальный размер
    for (Person person : persons) {
      if (!map.containsKey(person.id())) {
        map.put(person.id(), convertPersonToString(person));
      }
    }
    return map;
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // воспользуемся Hashset, т.к. у него временная сложность O(1)
    // тем самым избавимся от одного цикла
    Set<Person> persons1Set = new HashSet<>(persons1);
    for (Person person2 : persons2) {
      if (persons1Set.contains(person2)) {
        return true; // нашли совпадение - сразу выходим
      }
    }
    return false; //если не нашли false
    // еще нашел такой метод return !Collections.disjoint(persons1, persons2);, но так вообще неинтересно

  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0)
            .count();
    // не совсем уверен здесь, но как я понял, не рекомендуется изменять общую переменную
    // в общем, в прошлом случае возникают сложности с отладкой в большой программе
    // и трудности при поиске ошибок
    }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
  //assert всегда верен, потому что хеш-код Integer
  // равен самому числу, поэтому все числа от 1 до 10000 попадают в уникальные бакеты,
  // индексы которых совпадают с числами. HashSet обходит бакеты по возрастанию индексов,
  // поэтому метод toString() возвращает числа в упорядоченном виде
}
