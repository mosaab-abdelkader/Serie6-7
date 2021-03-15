package org.douifi.Exercice15;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;





public class ActorsAndMovies {

    public static void main(String[] args) {

        ActorsAndMovies actorsAndMovies = new ActorsAndMovies();
        Set<Movie> movies = actorsAndMovies.readMovies();
        //Q1
        System.out.println("Q1\n# movies.size()) = " + movies.size());
        
        //Q2
//        
//        	List<Actor> actor = movies.stream()
//        		.flatMap(movie -> movie.actors().stream())
//        		.collect(Collectors.toList());
//        	
//        	System.out.println("# Actors avec doublant : "+actor.size());
        	Set<Actor> actors = movies.stream()
            		.flatMap(movie -> movie.actors().stream())
            		.collect(Collectors.toSet());
            	
            System.out.println("Q2\n# Actors sans doublan : "+actors.size());
            
          //Q3 
        	long countNmbrYear = movies.stream()
        		.map(movie -> movie.releaseYear() )
        		.distinct()
        		.count();
        	
        	System.out.println("Q3\n# nember of distinct years in file "+countNmbrYear);

        	//Q4 date du film plus vieux et le plus recent 
    		
    		Integer  MaxYear = movies.stream()
					   .map(m -> m.releaseYear())
				       .max((o1, o2) -> Integer.compare(o1, o2))
				       .get();
    		Integer  MinYear = movies.stream()
					   .map(m -> m.releaseYear())
				       .min(Integer::compare)
				       .get();
    		System.out.println("Q4\nThe recent film was in: "+MaxYear);
    		
    		System.out.println("The oldest film was in : " +MinYear);
    		//Q5
    		System.out.println("Q5\n# Film by year");
        		
        		Map<Integer, Long> map = 
        				  movies.stream()
	        					.collect(
	        						Collectors.groupingBy(
	        							Movie -> Movie.releaseYear(),
	        							Collectors.counting()
	        						)
	        					);
        		map.forEach((key, value) -> System.out.println(key + " = " + value));
        		Entry<Integer, Long> maxNbrOfmovie = 
        				map.entrySet().stream() 
        					.max(Comparator.comparing(Entry::getValue))
        					.orElseThrow();
        		System.out.println("in wich year max of film has been relased ? :"+maxNbrOfmovie.getKey());
        		System.out.println(" How many film ? :"+maxNbrOfmovie.getValue());
        		
        		//Q6	
        	Comparator<Movie> cmp = Comparator.comparing(m -> m.actors().size());
        	
        	Movie movieWithMaxActors = 
        							movies.stream()
        							.max(cmp).orElseThrow();
        		System.out.println("Q6\n movie With Max Actors :"+movieWithMaxActors.title());
        		//6max actor in film
            	
            	int maxActor = movies.stream()
            			.mapToInt(m -> m.actors().size())
            			.max().orElseThrow();
            	System.out.println("Nbr of actors in this Film : "+maxActor);
        		//7 
            	// first map of actors and nbr of filme played
            	Map<Actor,Long> map1 =
	            			movies.stream()
	                		.flatMap(movie -> movie.actors().stream())
	                		.collect(
	                				Collectors.groupingBy(
	                						Function.identity(),
	                						Collectors.counting()
	                						)
	                		);
	            // actor for test
            	Actor Don= new Actor("Adams", "Dani") ;
            	
            	System.out.println("Q7\n test : Adams Don played  in "+ map1.get(Don)+" Film") ;
            	//7 actor played in max nbr of film
        	
            	   Map.Entry<Actor, Long> ActorPlaymax = map1.entrySet().stream()
            				   .max(Comparator.comparing(Map.Entry::getValue))
            				   .orElseThrow();
            	System.out.println("\nActor has played max nbr of time :"+ActorPlaymax);
            	
            	//Q8 
            	//now using just Collector
            			
            	 Collector<Movie, Object, Entry<Actor, Long>> SuperCollectors = Collectors.collectingAndThen(
								Collectors.flatMapping(
										movie -> movie.actors().stream() ,
										Collectors.groupingBy(
												Function.identity() ,
				        						Collectors.counting()
				        				)
								) ,
								ma-> ma.entrySet().stream()
							   .max(Comparator.comparing(Map.Entry::getValue))
							   .orElseThrow()
				);
				Map.Entry<Actor, Long> ActorPlaymaxByCollectors = movies.stream().collect(SuperCollectors);
            	System.out.println("Q8\n Actor has played max nbr of time :"+ActorPlaymaxByCollectors);
            	// actor played in max nbr of film in year
            	
            	Map<Integer,Entry<Actor,Long>> mapYear=
            			movies.stream()
            				  .collect(
            						  Collectors.groupingBy(Movie::releaseYear,SuperCollectors)
            						  );
            	
            	Entry<Integer, Entry<Actor, Long>> ActorPlayedmaxInYear = mapYear.entrySet().stream()
            	 				  .max(Comparator.comparing(m->m.getValue().getValue() ) )
				                  .orElseThrow();
            	System.out.println(" Actor has played max nbr of time in one Year :"+ActorPlayedmaxInYear);
            		
            	//Q9
            	//a
            	System.out.println("cc");
            	Comparator <Actor> cmpActor = Comparator.comparing((Actor a) -> a.lastName).thenComparing(a -> a.firstName) ;
            	//b
            	System.out.println("\n Q9-b");

            	BiFunction<Stream<Actor>, Actor,Stream< Map.Entry<Actor, Actor>>> pairActor = (Stream<Actor> t, Actor u) -> 
            		
            							t.filter(s-> cmpActor.compare(s,u)<0)
            							.map(act -> Map.entry(act, u));
            							
            	List<Actor> test=  List.of(
		            	 new Actor("a", "A") ,
		            	 new Actor("b","B" ) ,
		            	 new Actor("c","C" ) ,
		            	 new Actor("e","E" ) ,
		            	 new Actor("a","A" ) );

            	Actor D= new Actor("d","D" ) ;
            	Stream< Map.Entry<Actor, Actor>> test9bBiFun =pairActor.apply(test.stream(), D);
            	test9bBiFun.forEach( t -> System.out.println("("+t.getKey().lastName + " , " + t.getValue().lastName+")"));
        		//c
            	System.out.println("Q9-c");
            	Movie movieTest = 
						movies.stream().findAny().get();
            	Function<Movie,Stream<Actor>> MovieActors = movie -> movie.actors().stream();
            	Stream<Actor> test9c = MovieActors.apply(movieTest); 
            	
            	test9c.forEach( s -> System.out.println(s + ""));
        		//d
            	System.out.println("Q9-d");
            	BiFunction<Movie, Actor,Stream< Map.Entry<Actor, Actor>>> pairMovie = (Movie m, Actor a) -> pairActor.apply(MovieActors.apply(m),a);
        		
            	Stream<Entry<Actor, Actor>> pairMoviTest = pairMovie.apply(movieTest, Don);
            	pairMoviTest.forEach( t -> System.out.println("("+t.getKey().lastName + " , " + t.getValue().lastName+")"));
            	//e
            	System.out.println("Q9-e\n");
            	Function<Movie,Stream< Map.Entry<Actor, Actor>>> pairInMovie  = (Movie movie) -> MovieActors.apply(movie)
            																								.flatMap(ac -> pairMovie.apply(movie,ac) );	
            	
            	Stream<Entry<Actor, Actor>> test9e = pairInMovie.apply(movieTest);
            	test9e.forEach( t -> System.out.println("("+t.getKey().lastName + " , " + t.getValue().lastName+")"));
            	//f
            	System.out.println("Q9-f\n");
            	long countPair = movies
              		   .stream()
              		   .flatMap(film -> pairInMovie.apply(film))
              		   .count();
            	System.out.println("Nbr of pair in movie :"+countPair);
              		   
            	long countPairDistnct = movies
               		   .stream()
               		   .flatMap(film -> pairInMovie.apply(film))
               		   .distinct()                  	
               		   .count();
            	
            	System.out.println("Nbr of pair distinct in movie :"+countPairDistnct);
            	
            	//g
            	System.out.println("\n Q9-g ");
            	System.out.println("Most two actor has been plaied togather :"+movies
        		   .stream()
        		   .flatMap(m -> pairInMovie.apply(m))
        		   .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
        		   .entrySet()
        		   .stream()
        		   .max(Comparator.comparing(Entry:: getValue))
        		   .orElseThrow());
            	
            	System.out.println("\n Q10 ");
            	Collector<Movie, Integer, Entry<Entry<Actor, Actor>, Long>> pairesCollector =
            	Collectors.collectingAndThen(
            			Collectors.flatMapping(f -> pairInMovie.apply(f), 
            					Collectors.groupingBy(Function.identity(),Collectors.counting()))
            			,
            			mapT -> mapT.entrySet().stream()
            			.max(Comparator.comparing(Entry:: getValue))
            			.orElseThrow());
            	Entry<Integer, Entry<Entry<Actor, Actor>, Long>> mostInOneYear = movies
        		   .stream().collect(Collectors.groupingBy(Movie::releaseYear,pairesCollector))
        		   .entrySet().stream()
        		   .max(Comparator.comparing(ent -> ent.getValue().getValue()))
        		   .orElseThrow();
        		System.out.println("Most two actor has been plaied togather in one year :"+mostInOneYear);
    }

    public Set<Movie> readMovies() {

        Function<String, Stream<Movie>> toMovie =
                line -> {
                    String[] elements = line.split("/");
                    String title = elements[0].substring(0, elements[0].lastIndexOf("(")).trim();
                    String releaseYear = elements[0].substring(elements[0].lastIndexOf("(") + 1, elements[0].lastIndexOf(")"));
                    if (releaseYear.contains(",")) {
                        // Movies with a coma in their title are discarded
                    	int indexOfcoma = releaseYear.indexOf(",");
                    	releaseYear =releaseYear.substring(0, indexOfcoma);
                      //  return Stream.empty();
                    }
                    Movie movie = new Movie(title, Integer.valueOf(releaseYear));


                    for (int i = 1; i < elements.length; i++) {
                        String[] name = elements[i].split(", ");
                        String lastName = name[0].trim();
                        String firstName = "";
                        if (name.length > 1) {
                            firstName = name[1].trim();
                        }

                        Actor actor = new Actor(lastName, firstName);
                        movie.addActor(actor);
                    }
                    return Stream.of(movie);
                };

        try (FileInputStream fis = new FileInputStream("files/movies-mpaa.txt.gz");
             GZIPInputStream gzis = new GZIPInputStream(fis);
             InputStreamReader reader = new InputStreamReader(gzis);
             BufferedReader bufferedReader = new BufferedReader(reader);
             Stream<String> lines = bufferedReader.lines();
        ) {

            return lines.flatMap(toMovie).collect(Collectors.toSet());

        } catch (IOException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        return Set.of();
    }
}
