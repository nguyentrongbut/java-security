package com.example.spingsercurewebservicev2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader {

  @Bean
  public CommandLineRunner commandLineRunner(UserRepository userRepository,
                                             RoleRepository roleRepository,
                                             PublisherRepository publisherRepository,
                                             BookRepository bookRepository,
                                             PasswordEncoder passwordEncoder) {
    return args -> {
      // Tạo và lưu các vai trò
      Role adminRole = new Role();
      adminRole.setName("ADMIN");
      roleRepository.save(adminRole);

      Role employeeRole = new Role();
      employeeRole.setName("EMPLOYEE");
      roleRepository.save(employeeRole);

      Role publicRole = new Role();
      publicRole.setName("PUBLIC");
      roleRepository.save(publicRole);

      // Tạo và lưu người dùng quản trị
      AppUser admin = new AppUser();
      admin.setUsername("admin");
      admin.setPassword(passwordEncoder.encode("adminpass"));
      admin.setRoles(Set.of(adminRole));
      userRepository.save(admin);

      // Tạo và lưu nhân viên
      AppUser employee = new AppUser();
      employee.setUsername("employee");
      employee.setPassword(passwordEncoder.encode("employeepass"));
      employee.setRoles(Set.of(employeeRole));
      userRepository.save(employee);

      // Tạo và lưu người dùng thông thường
      AppUser user = new AppUser();
      user.setUsername("user");
      user.setPassword(passwordEncoder.encode("userpass"));
      user.setRoles(Set.of(publicRole));
      userRepository.save(user);

      // Tạo và lưu các nhà xuất bản
      Publisher publisher1 = new Publisher();
      publisher1.setName("Publisher 1");
      publisherRepository.save(publisher1);

      Publisher publisher2 = new Publisher();
      publisher2.setName("Publisher 2");
      publisherRepository.save(publisher2);

      // Tạo và lưu các sách
      Book book1 = new Book();
      book1.setTitle("Book 1");
      book1.setAuthor("Author 1");
      book1.setPublisher(publisher1);
      bookRepository.save(book1);

      Book book2 = new Book();
      book2.setTitle("Book 2");
      book2.setAuthor("Author 2");
      book2.setPublisher(publisher2);
      bookRepository.save(book2);
    };
  }
}
