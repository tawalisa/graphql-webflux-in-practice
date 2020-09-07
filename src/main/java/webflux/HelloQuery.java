package webflux;

import graphql.kickstart.tools.GraphQLQueryResolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Component
@Slf4j
class HelloQuery implements GraphQLQueryResolver {

  public CompletableFuture<String> hello() {
    return Mono.just("Hello world").toFuture();
  }
  public CompletableFuture<List<String>> test(DataFetchingEnvironment var) throws InterruptedException {
//    Disposable a = Flux.fromIterable(getDidstributions()).map(s -> s.split("1")).subscribe();
//            .subscribe(System.out::println, e-> e.printStackTrace());
    System.out.println("111111111111111"+var.getFieldDefinition().getName());
    Mono<String> login = doLogin();
    System.out.println("2222222222222222");

    Mono<List<String>> value = login.flatMap(info -> callRemoteService(info));
    System.out.println("33333333333333");
    Thread.sleep(3000L);
    return value.toFuture();
  }


  public CompletableFuture<String> test1() {
    log.info("=====");
    return Flux.fromArray(new String[]{"1", "2"}).parallel(2).runOn(Schedulers.parallel()).map(s-> {
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      log.info(Thread.currentThread().getName()+"--"+s);
      return 1+",=";
    }).sequential().buffer().single().map(list-> list.toString()).toFuture();
  }
  public static void main(String[] args) {
//    new HelloQuery().test();


  }

  private static Mono<? extends List<String>> callRemoteService(String info) {
      return Mono.fromCallable(()->{
        Thread.sleep(1000L);
        System.out.println("555555555555555");
        List<String> list = new ArrayList();
        list.add(1+"");
        list.add(2+"");
        return list;
      });
  }

  private static Mono<String> doLogin() {
    System.out.println("444444444444");
    return Mono.fromCallable(()->{
      Thread.sleep(1000L);
      System.out.println("66666666666666");

      return "list";
    });
  }


  private List<String> getDidstributions() {
    List list = new ArrayList();
    for(int i = 0;i < 10; i++){
      list.add(i+"");
    }
    return list;
  }


}
