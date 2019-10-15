package alteca.fr.demojwt.web;

import alteca.fr.demojwt.Repository.ApplicationUserRepository;
import alteca.fr.demojwt.Repository.RoleDao;
import alteca.fr.demojwt.domain.ApplicationUser;
import alteca.fr.demojwt.domain.Role;
import alteca.fr.demojwt.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody ApplicationUser applicationUser) {

        ApplicationUser user = new ApplicationUser( applicationUser.getUsername(),
                encoder.encode(applicationUser.getPassword()));
        Set<Role> strRoles = applicationUser.getRoles();
        user.setRoles(strRoles);
        applicationUserRepository.save(user);
        return ResponseEntity.ok().body("User registered successfully!");
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody ApplicationUser applicationUser) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        applicationUser.getUsername(),
                        applicationUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
       return  new ResponseEntity<>(jwt, HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
