package alteca.fr.demojwt.web;

import alteca.fr.demojwt.Repository.ApplicationUserRepository;
import alteca.fr.demojwt.domain.ApplicationUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest
public class AuthRestAPIsTest {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Test
    public void findByUsername() {
        ApplicationUser user =applicationUserRepository.findByUsername("admin").get();
        assertNotNull("user exist",user);
    }
}
