# SpringBoot-Security

# Create Proejct

    Create a maven project 
    Add following dependencies in pom xml
    <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>2.1.3.RELEASE</version>
       <relativePath></relativePath>
    </parent>

     <dependencies>
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-web</artifactId>
         </dependency>
     </dependencies>

     <build>
         <plugins>
             <plugin>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-maven-plugin</artifactId>
             </plugin>
         </plugins>
     </build>
     
  # Create Main Class 
    
      @SpringBootApplication
    public class SpringBootSecurityApplication {

    public static void main(String []args){
        SpringApplication.run(SpringBootSecurityApplication.class,args);
      }
    }
 
 # create resource class 
 
 @RestController
  public class HomeResource {

      @GetMapping("/")
      public String home(){
          return ("<h1>Welcome</h1>");
      }
    }
    
# Test
    http://localhost:8080/

# Add Spring Security 
        update pom with following dependecy
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        
        Restart the Application 
        and access http://localhost:8080/login 
        its open login page with username and password its default page due to security dependecy 
        this is added as a part of filter 
        credentials are : username is user and password will check in console as 
        Using generated security password: e6ec18c8-bd84-4eae-80fb-5deb977878f6
        change the default behaviour by adding the following lines in application.properties 
        
        spring.security.user.name=test
        spring.security.user.password=test

        Restarted application and test with login.
        
 # spring security default behaviour 
  1. Add mandatory authentication for all URLs
  2. added Login form 
  3. Handles login error 
  
# configure authentication
  
     instead of hard code as above application properies we use here in memory to store credentials 
     
     Remove the added details on application.properties file 
     
     @EnableWebSecurity
    public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // set your configuration on the auth object

        auth.inMemoryAuthentication()
                .withUser("blah")
                .password("blah")
                .roles("USER")
                .and()
                .withUser("foo")
                .password("foo")
                .roles("ADMIN");

    }

      @Bean
      public PasswordEncoder getPasswordEncoder(){
          return NoOpPasswordEncoder.getInstance();
      }
    }
    
    
      
      

  
    
