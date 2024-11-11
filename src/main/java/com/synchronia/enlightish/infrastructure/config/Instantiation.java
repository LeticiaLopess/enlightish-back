package com.synchronia.enlightish.infrastructure.config;

import com.synchronia.enlightish.domain.entity.Address;
import com.synchronia.enlightish.domain.entity.Lead;
import com.synchronia.enlightish.domain.entity.User;
import com.synchronia.enlightish.domain.enumeration.Status;
import com.synchronia.enlightish.infrastructure.repository.AddressRepository;
import com.synchronia.enlightish.infrastructure.repository.LeadRepository;
import com.synchronia.enlightish.infrastructure.repository.UserRepository;
import com.synchronia.enlightish.presentation.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;

@Configuration
@Profile("development")
public class Instantiation implements CommandLineRunner {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public void run(String... args) throws Exception {

        Instant birthDate1 = Instant.parse("2000-06-29T00:00:00Z");
        Instant birthDate2 = Instant.parse("1963-06-22T00:00:00Z");

        String password1 = Base64.getEncoder().encodeToString("1234".getBytes());
        String password2 = Base64.getEncoder().encodeToString("1234".getBytes());

        Address address1 = new Address("Balmoral Avenue", "1", "Balmoral", "Edinburgh", null, "Scotland", "United Kingdom", "EH1 2NG");
        Address address2 = new Address("Londonderry Road", "45", "Notting Hill", "London", null, "England", "United Kingdom", "W11 2BS");
        addressRepository.saveAll(Arrays.asList(address1, address2));

        User user1 = new User("leticialopes", password1, "Leticia Lopes", "leticia@gmail.com", "2140028922",  birthDate1, Status.ACTIVE, address1);
        User user2 = new User("joaorodolfo", password2, "João Lopes", "joaorodolfo@gmail.com", "2195959595",  birthDate2, Status.INACTIVE, address2);
        userRepository.saveAll(Arrays.asList(user1, user2));

        Lead lead1 = new Lead("Emma", "emma@gmail.com", "21940028944", birthDate1, null);
        Lead lead2 = new Lead("Edward", "edward@gmail.com", "21940028897", birthDate2, null);
        leadRepository.saveAll(Arrays.asList(lead1, lead2));

    }

}
