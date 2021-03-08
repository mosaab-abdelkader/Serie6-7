package org.douifi.Exercice14;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Exo14 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	
		Stream<String> strings = Stream.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
			"eleven", "twelve");
		//Q1

		System.out.println("Question 1 \n");
		 System.out.println(" Liste of strings : ");
		 strings.forEach(s -> System.out.println(s));
		
		//Q2

			System.out.println("\nQuestion 2 ");
		List<String> strings3 = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
				"eleven", "twelve");
		List<String> liMajStrings = strings3.stream()
											.map(s->s.toUpperCase())
										    .collect(Collectors.toList());
		System.out.println("\nListe of strings : "+liMajStrings);
		//Q3

		System.out.println("\nQuestion 3 ");
		Set<String> liMajFirst = strings3.stream()
											.map(s->s.substring(0, 1).toUpperCase())
										    .collect(Collectors.toSet());
		System.out.println("\nListe of strings : "+liMajFirst);
		//Q4

		System.out.println("\nQuestion 4 ");
		Set<Integer> stremLength = strings3.stream()
											.map(s->s.length())
										    .collect(Collectors.toSet());
		System.out.println("\nListe of strings : "+stremLength);
		//Q5

		System.out.println("\nQuestion 5 ");
		long nbrStrings = strings3.stream().count();
		System.out.println("\nNombre of elements : "+nbrStrings);
		
		//Q6

		System.out.println("\nQuestion 6 ");
		Long  stremLengthPair = strings3.stream()
											.map(s->s.length())
										    .filter(i -> i%2 == 0)
										    .count();

		System.out.println("\nNombre of pair elements : "+ stremLengthPair);
		//Q7

		System.out.println("\nQuestion 7 ");
		Integer  stremLengthMax = strings3.stream()
									   .map(String::length)
								       .max((o1, o2) -> Integer.compare(o1, o2))
								       .get();

		System.out.println("\n length  of the bigest element : "+ stremLengthMax);

		//Q8

		System.out.println("\nQuestion 8 ");
		List<String> listMajImpair = strings3.stream()
											.filter(s -> s.length()%2!=0)
											.map(s->s.toUpperCase())
										    .collect(Collectors.toList());
		System.out.println("\nListe of impair strings in Maj : "+listMajImpair);
		
		//Q9

		System.out.println("\nQuestion 9 ");
		String stringOfStremsLess5 = strings3.stream()
											.filter(s -> s.length() <=5)
											.sorted()
										    .collect(Collectors.joining(",","{","}"));
		System.out.println("\n string Of Strems Less or Equal to 5 : "+stringOfStremsLess5);
		
		//Q10

		System.out.println("\nQuestion 10 ");
		Map<Integer, List<String>> map =
							strings3.stream()
						    .collect(Collectors.groupingBy(String::length));
		map.forEach((key ,value)-> System.out.println(key+" =  "+value ));
							
		//Q11

		System.out.println("\nQuestion 11 ");
		Map<String, String> map1 =
							 strings3.stream()
						    .collect(Collectors.groupingBy(s -> s.substring(0,1)
						    								,Collectors.joining(", ")));
		map1.forEach((key ,value)-> System.out.println(key+" =  "+value ));
		
		
		
		
	}

}
