package com.esoft.tpdevops2.cucumber;

import com.esoft.tpdevops2.entities.User;
import com.esoft.tpdevops2.repositories.UserRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserStepDefinition {

    @Autowired
    private UserRepository userRepository;

    private List<User> userList;

    @Before
    public void setup() {
        userRepository.deleteAll();
    }

    @Given("une base de données vide")
    public void une_base_de_donnees_vide() {
        userRepository.deleteAll();
        assertThat(userRepository.count()).isZero();
    }

    @When("je crée un utilisateur {string} avec le prénom {string} et le nom {string}")
    public void je_cree_un_utilisateur(String firstName, String ignored, String lastName) {
        User user = new User(null, firstName, lastName);
        userRepository.save(user);
    }

    @Then("l'utilisateur {string} existe dans la base")
    public void l_utilisateur_existe(String firstName) {
        List<User> users = userRepository.findAll();
        assertThat(users).anyMatch(u -> u.getFirstName().equals(firstName));
    }

    @Given("les utilisateurs suivants existent:")
    public void les_utilisateurs_suivants_existent(DataTable dataTable) {
        userRepository.deleteAll();
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            User user = new User(
                    row.get("id") != null ? Long.parseLong(row.get("id")) : null,
                    row.get("firstName"),
                    row.get("lastName")
            );
            userRepository.save(user);
        }
    }

    @When("je demande la liste des utilisateurs")
    public void je_demande_la_liste() {
        userList = userRepository.findAll();
    }

    @Then("je reçois {int} utilisateurs")
    public void je_recois_utilisateurs(int count) {
        assertThat(userList).hasSize(count);
    }

    @Given("un utilisateur {string} existe avec l'ID {int}")
    public void un_utilisateur_existe_avec_id(String firstName, int id) {
        User user = new User((long) id, firstName, "DefaultLastName");
        userRepository.save(user);
    }

    @When("je supprime l'utilisateur avec l'ID {int}")
    public void je_supprime_utilisateur(int id) {
        userRepository.deleteById((int) id);
    }

    @Then("l'utilisateur avec l'ID {int} n'existe plus")
    public void utilisateur_n_existe_plus(int id) {
        assertThat(userRepository.existsById((int) id)).isFalse();
    }
}
