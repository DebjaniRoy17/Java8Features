package com.demo.streams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StreamsApplicationTests {

	@Test
	void contextLoads() {
	}
	
	private static Employee[] arrayOfEmps = {
		    new Employee(1, "Jeff Bezos", 100000.0), 
		    new Employee(2, "Bill Gates", 200000.0), 
		    new Employee(3, "Mark Zuckerberg", 300000.0)
		};
	private static List<Employee> empList = Arrays.asList(arrayOfEmps);
	
	@Test
	public void testStreamCreationOne() {
		
		Stream<Employee> empStream = empList.isEmpty() ? Stream.empty() : empList.stream();
		assertEquals(3, empStream.count());
	}
	@Test
	public void testStreamCreationPartOfArray() {
		Stream<Employee> empStream =  Arrays.stream(arrayOfEmps,1,3);
		assertEquals(2, empStream.count());
	}
	@Test
	public void testStreamCreationBuilder() {
		Stream<String> streamBuilder =
				  Stream.<String>builder().add("a").add("b").add("c").build();
		assertEquals(3,streamBuilder.count());
	}
	@Test
	public void testStreamFilter() {
		Employee emp = empList.stream().filter(name -> "Jeff Bezos".equals(name.getEmpName())).findAny().orElse(null);
		assertNotEquals("Bill Gates",emp.getEmpName());
		assertEquals("Jeff Bezos", emp.getEmpName());

	}
	@Test
	public void testStreamIterate() {
		Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);	
		
		assertEquals(20, streamIterated.count());
		

	}
	@Test
	public void testStreamCollectors() {
		List<String> elements =
			  Stream.of("a", "b", "c").filter(element -> element.contains("b"))
			    .collect(Collectors.toList());
			Optional<String> anyElement = elements.stream().findAny();
			Optional<String> firstElement = elements.stream().findFirst();
			
			assertThat(anyElement.equals("b"));
			assertThat(firstElement.equals("a"));
	}
	@Test
	public void testStreamCollectorsTwo() {
		List<String> elements =
				  Stream.of("a", "b", "c").collect(Collectors.toList());
		
		assertThat(elements.contains("b"));
	}
	@Test
	public void testStreamCollectorsThree() {
		List<String> empCollection = 
				  empList.stream().map(Employee::getEmpName).collect(Collectors.toList());
		
		empCollection.forEach(System.out::println);
	}
	@Test
	public void testStreamCollectorsFour() {
		String empNamesInArrayFormat = 
				  empList.stream().map(Employee::getEmpName).collect(Collectors.joining(", ", "[", "]"));
		
		System.out.println(empNamesInArrayFormat);
	}
	@Test
	public void testStreamMorethanOneModfications() {
		Stream<Object> empstream = empList.stream().skip(1).map(element -> element.getEmpName().substring(0,3));
		
		
		assertEquals(2,empstream.count());
		
	}
	@Test
	public void testStreamReduced() {
		OptionalInt reduced =
				  IntStream.range(1, 4).reduce((a, b) -> a + b);
		System.out.println(reduced);
		
		int reducedTwoParams =
				  IntStream.range(1, 4).reduce(10, (a, b) -> a + b);
		System.out.println(reducedTwoParams);
		
		int reducedParallel = Arrays.asList(1, 2, 3).parallelStream()
			    .reduce(10, (a, b) -> a + b, (a, b) -> {
			       
			       return a + b;
			    });
		System.out.println(reducedParallel);
	}
	@Test
	public void testStreamAverageDouble() {
		double empSalary = empList.stream().collect(Collectors.averagingDouble(Employee::getEmpSalary));
		
		System.out.println(empSalary);
	}
	@Test
	public void testStreamSummingDouble() {
		double empSalary = empList.stream().collect(Collectors.summingDouble(Employee::getEmpSalary));
		
		System.out.println(empSalary);
	}
	@Test
	public void testStreamSummarizingDouble() {
		DoubleSummaryStatistics empSalary = empList.stream().collect(Collectors.summarizingDouble(Employee::getEmpSalary));
		
		System.out.println(empSalary);
	}
	
	public Future<String> calculateAsync() throws InterruptedException{
		CompletableFuture<String> complete = new CompletableFuture<String>();
		
		Executors.newCachedThreadPool().submit(() -> {
			Thread.sleep(50);
			complete.complete("Hello");
			return null;
		});
		return complete;
	}
	
	@Test
	public void testCompletableFutureComplete() throws InterruptedException, ExecutionException {
		Future<String> future = calculateAsync();
		String result = future.get();
		assertEquals("Hello", result);
	}
	public Future<String> calculateAsyncCancel() throws InterruptedException{
		CompletableFuture<String> complete = new CompletableFuture<String>();
		
		Executors.newCachedThreadPool().submit(() -> {
			Thread.sleep(50);
			complete.cancel(true);
			return null;
		});
		return complete;
	}
	
	@Test
	public void testCompletableFutureCancel() throws InterruptedException, ExecutionException {
		Future<String> future = calculateAsync();
		String result = future.get();
		assertEquals("Hello", result);
	}
	
	@Test
	public void testCompletableFutureRunAsync() throws InterruptedException, ExecutionException {
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			try {
				calculateAsync();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		assertEquals(null, future.get());
	}
	
	@Test
	public void testCompletableFutureSupplyAsync() throws InterruptedException, ExecutionException {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
		assertEquals("Hello", future.get());
	}
	
	@Test
	public void testCompletableFutureThenApply() throws InterruptedException, ExecutionException {
		CompletableFuture<String> comfuture = CompletableFuture.supplyAsync(() -> "Hello");
		Future<String> future = comfuture.thenApply(s -> s+" World");
		assertEquals("Hello World", future.get());
	}
	
	@Test
	public void testCompletableFutureThenAccept() throws InterruptedException, ExecutionException {
		CompletableFuture<String> comfuture = CompletableFuture.supplyAsync(() -> "Hello");
		Future<Void> future = comfuture.thenAccept(s -> System.out.println(comfuture));//thenAccept() accepts a consumer as a Lambda
		future.get();
	}
	
	@Test
	public void testCompletableFutureThenRun() throws InterruptedException, ExecutionException {
		CompletableFuture<String> comfuture = CompletableFuture.supplyAsync(() -> "Hello");
		Future<Void> future = comfuture.thenRun(() -> System.out.println(comfuture));//thenRun() accepts a runnable as a Lambda
		future.get();
	}
	
	@Test
	public void testCompletableFutureThenCompose() throws InterruptedException, ExecutionException {
		CompletableFuture<String> comfuture = CompletableFuture.supplyAsync(() -> "Hello")
				.thenCompose(s -> CompletableFuture.supplyAsync(() -> s+" World"));
		
		assertEquals("Hello World", comfuture.get());
	}
	
	@Test
	public void testCompletableFutureAllOf() throws InterruptedException, ExecutionException {
		CompletableFuture<String> comfuture1 = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<String> comfuture2 = CompletableFuture.supplyAsync(() -> "Beautiful");
		CompletableFuture<String> comfuture3 = CompletableFuture.supplyAsync(() -> "World");
		
		assertTrue(comfuture1.isDone());
		assertTrue(comfuture2.isDone());
		assertTrue(comfuture3.isDone());
		
		CompletableFuture<Void> combined = CompletableFuture.allOf(comfuture1,comfuture2,comfuture3);
		combined.get();
		
		String finalstring = Stream.of(comfuture1,comfuture2,comfuture3).map(CompletableFuture::join).collect(Collectors.joining(" "));
		
		assertEquals("Hello Beautiful World", finalstring);
	}
	
	@Test
	public void testCompletableFutureHandle() throws InterruptedException, ExecutionException {
		
		String name = null;
		
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			if(name == null) {
				throw new RuntimeException();
			}
			return "Hello "+name;
		}).handle((s,t) -> s!=null?s:"Hello Stranger");
		
		assertEquals("Hello Stranger", future.get());
	}
}
