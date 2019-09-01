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
    
  # Autherization 
        Add the followinig methods into response class
             /**
             * this is able to access by only admin
             * @return
             */
            @GetMapping("/admin")
            public String admin(){
                return ("<h1>Welcome Admin</h1>");
            }

            /**
             * this is able to access by admin and user
             * @return
             */
            @GetMapping("/user")
            public String user(){
                return ("<h1>Welcome User</h1>");
            }
    
    
        Add the following method in SecurityConfiguration class
        @Override
    public void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .antMatchers("/**").hasAnyRole("ADMIN")
               .and().formLogin();

    }
    
   restart the application and test using user role credentails as blah/blah 
   we get the forbidden 403 error we need to logout and try to login by admin credentails but we not have logout option try the following url as http://localhost:8080/logout it ask prompt and say logout and try admin credentials.
   
   Change the following method to add a logic for different apis able to login by autherization 
   
   @Override
    public void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .antMatchers("/admin").hasAnyRole("ADMIN")
               .antMatchers("/user").hasAnyRole("USER","ADMIN")
               .antMatchers("/").permitAll()
               .and().formLogin();

    }
    
    Test these by using 
      login :- blah / blah as a user  by using http://localhost:8080/login
         access http://localhost:8080/user -- sucess 
                http://localhost:8080 -- success
                http://localhost:8080/admin -- failed with 403 error 
                
       logout :- http://localhost:8080/logout and login as admin by foo/foo 
       access :- http://localhost:8080/user -- success
                 http://localhost:8080 -- success
                 http://localhost:8080/admin -- success 
      
    
    
      

  
    
