package alteca.fr.demojwt.web;

import alteca.fr.demojwt.Exception.EmptyFileException;
import alteca.fr.demojwt.Repository.ApplicationUserRepository;
import alteca.fr.demojwt.domain.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RestController
public class feedBdAPIs {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private AuthRestAPIs authRestAPIs;

    @PostMapping("/feed")
    public List<String> feedBD(@RequestParam MultipartFile file) throws EmptyFileException, IOException {
        if(file.isEmpty())
            throw new EmptyFileException("fichier vide");
            List<String> listUsers = new ArrayList<>();
            byte[] fileBytes = file.getBytes();
            String completeData = new String(fileBytes);
            String[] data = completeData.split("#");
            String[] rowData = data[0].split(System.getProperty("line.separator" ));

            Iterator<String> iterator = Arrays.stream(rowData).iterator();
            while(iterator.hasNext()) {
                String[] userLine = iterator.next().split(";");
                if(applicationUserRepository.existsByUsername(userLine[0]))
                    listUsers.add("erreur "+userLine[0]);
                else {
                    ApplicationUser user = new ApplicationUser(userLine[0], userLine[1]);
                    ApplicationUser u = applicationUserRepository.save(user);
                    listUsers.add("Ajout réussi "+u.getUsername());
                }
            }
            return listUsers;
    }
}
