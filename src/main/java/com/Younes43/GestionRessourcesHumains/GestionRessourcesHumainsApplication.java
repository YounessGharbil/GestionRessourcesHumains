package com.Younes43.GestionRessourcesHumains;

import com.Younes43.GestionRessourcesHumains.Entities.ApplicationUser;
import com.Younes43.GestionRessourcesHumains.Entities.Enums.*;
import com.Younes43.GestionRessourcesHumains.Entities.Salarie;
import com.Younes43.GestionRessourcesHumains.Repositories.DemandeSanctionRepositories.*;
import com.Younes43.GestionRessourcesHumains.Services.DemandeSanctionServices.*;
import com.Younes43.GestionRessourcesHumains.Services.SalarieService;
import com.Younes43.GestionRessourcesHumains.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class GestionRessourcesHumainsApplication implements CommandLineRunner {


	private final SalarieService salarieService;

	private final PasswordEncoder passwordEncoder;

	private final UserService service;


	private final RapportTeamLeaderRepository rapportChefDirectRepository;

	private final DemandeDeSanctionService demandeDeSanctionService;

	private final RapportTeamLeaderService rapportChefDirectService;

	private final RapportSuperviseurService rapportShiftLeaderService;

	private final RapportManagerService rapportChefDepartementService;

	private final RaportRhService raportRhService;

	private final RapportRhPlus1Service rapportDirectionService;

	private final DemandeDeSanctionRepository demandeDeSanctionRepository;



	public static void main(String[] args) {
		SpringApplication.run(GestionRessourcesHumainsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*Salarie salarie11=Salarie.builder()
				.bu(BusinessUnit.GLOBAL_FINANCE)
				.genre(Genre.MALE)
				.departement("departement"+" "+1)
				.local_job_title("local_job_title"+" "+11)
				.type_de_contrat(TypeContrat.ANAPEC)
				.supervisor("supervisor"+" "+1)
				.segment(Segment.INDUSTRIAL_SOLUTIONS)
				.code_site(CodeSite.L74)
				.position("position"+" "+11)
				.nom("nom"+" "+11)
				.prenom("prenom"+" "+11)
				.site(Site.TAC1_AUTO)
				.status("status"+" "+11)
				.build();
		salarieService.createSalarie(salarie11);
		Salarie salarie1=Salarie.builder()
				.bu(BusinessUnit.GLOBAL_FINANCE)
				.genre(Genre.MALE)
				.departement("departement"+" "+1)
				.local_job_title("local_job_title"+" "+1)
				.type_de_contrat(TypeContrat.ANAPEC)
				.supervisor("supervisor"+" "+1)
				.segment(Segment.INDUSTRIAL_SOLUTIONS)
				.code_site(CodeSite.L74)
				.position("position"+" "+1)
				.nom("nom"+" "+1)
				.prenom("prenom"+" "+1)
				.site(Site.TAC1_AUTO)
				.status("status"+" "+1)
				.build();

		Salarie salarie2=Salarie.builder()
				.bu(BusinessUnit.GLOBAL_FINANCE)
				.genre(Genre.MALE)
				.departement("departement"+" "+1)
				.local_job_title("local_job_title"+" "+1)
				.type_de_contrat(TypeContrat.ANAPEC)
				.supervisor("supervisor"+" "+1)
				.segment(Segment.INDUSTRIAL_SOLUTIONS)
				.code_site(CodeSite.L74)
				.position("position"+" "+1)
				.nom("nom"+" "+1)
				.prenom("prenom"+" "+1)
				.site(Site.TAC1_AUTO)
				.status("status"+" "+1)
				.build();

		Salarie salarie3=Salarie.builder()
				.bu(BusinessUnit.GLOBAL_FINANCE)
				.genre(Genre.MALE)
				.departement("departement"+" "+1)
				.local_job_title("local_job_title"+" "+1)
				.type_de_contrat(TypeContrat.ANAPEC)
				.supervisor("supervisor"+" "+1)
				.segment(Segment.INDUSTRIAL_SOLUTIONS)
				.code_site(CodeSite.L74)
				.position("position"+" "+1)
				.nom("nom"+" "+1)
				.prenom("prenom"+" "+1)
				.site(Site.TAC1_AUTO)
				.status("status"+" "+1)
				.build();

		Salarie salarie4=Salarie.builder()
				.bu(BusinessUnit.GLOBAL_FINANCE)
				.genre(Genre.MALE)
				.departement("departement"+" "+1)
				.local_job_title("local_job_title"+" "+1)
				.type_de_contrat(TypeContrat.ANAPEC)
				.supervisor("supervisor"+" "+1)
				.segment(Segment.INDUSTRIAL_SOLUTIONS)
				.code_site(CodeSite.L74)
				.position("position"+" "+1)
				.nom("nom"+" "+1)
				.prenom("prenom"+" "+1)
				.site(Site.TAC1_AUTO)
				.status("status"+" "+1)
				.build();

		Salarie salarie5=Salarie.builder()
				.bu(BusinessUnit.GLOBAL_FINANCE)
				.genre(Genre.MALE)
				.departement("departement"+" "+1)
				.local_job_title("local_job_title"+" "+1)
				.type_de_contrat(TypeContrat.ANAPEC)
				.supervisor("supervisor"+" "+1)
				.segment(Segment.INDUSTRIAL_SOLUTIONS)
				.code_site(CodeSite.L74)
				.position("position"+" "+1)
				.nom("nom"+" "+1)
				.prenom("prenom"+" "+1)
				.site(Site.TAC1_AUTO)
				.status("status"+" "+1)
				.build();

		Salarie salarie6=Salarie.builder()
				.bu(BusinessUnit.INDUSTRIAL)
				.genre(Genre.FEMALE)
				.departement("departement"+" "+6)
				.local_job_title("local_job_title"+" "+6)
				.type_de_contrat(TypeContrat.CDI)
				.supervisor("supervisor"+" "+6)
				.segment(Segment.TRANSPORTATION_SOLUTIONS)
				.code_site(CodeSite.N61)
				.position("position"+" "+6)
				.nom("nom"+" "+6)
				.prenom("prenom"+" "+6)
				.site(Site.TAC1_AUTO)
				.status("status"+" "+6)
				.build();
		Salarie salarie7=Salarie.builder()
				.bu(BusinessUnit.INDUSTRIAL)
				.genre(Genre.FEMALE)
				.departement("departement"+" "+6)
				.local_job_title("local_job_title"+" "+6)
				.type_de_contrat(TypeContrat.CDI)
				.supervisor("supervisor"+" "+6)
				.segment(Segment.TRANSPORTATION_SOLUTIONS)
				.code_site(CodeSite.N61)
				.position("position"+" "+6)
				.nom("nom"+" "+6)
				.prenom("prenom"+" "+6)
				.site(Site.TAC1_AUTO)
				.status("status"+" "+6)
				.build();
		Salarie salarie8=Salarie.builder()
				.bu(BusinessUnit.INDUSTRIAL)
				.genre(Genre.FEMALE)
				.departement("departement"+" "+6)
				.local_job_title("local_job_title"+" "+6)
				.type_de_contrat(TypeContrat.CDI)
				.supervisor("supervisor"+" "+6)
				.segment(Segment.TRANSPORTATION_SOLUTIONS)
				.code_site(CodeSite.N61)
				.position("position"+" "+6)
				.nom("nom"+" "+6)
				.prenom("prenom"+" "+6)
				.site(Site.TAC1_AUTO)
				.status("status"+" "+6)
				.build();
		Salarie salarie9=Salarie.builder()
				.bu(BusinessUnit.INDUSTRIAL)
				.genre(Genre.FEMALE)
				.departement("departement"+" "+6)
				.local_job_title("local_job_title"+" "+6)
				.type_de_contrat(TypeContrat.CDI)
				.supervisor("supervisor"+" "+6)
				.segment(Segment.TRANSPORTATION_SOLUTIONS)
				.code_site(CodeSite.N61)
				.position("position"+" "+6)
				.nom("nom"+" "+6)
				.prenom("prenom"+" "+6)
				.site(Site.TAC1_AUTO)
				.status("status"+" "+6)
				.build();
		Salarie salarie10=Salarie.builder()
				.bu(BusinessUnit.INDUSTRIAL)
				.genre(Genre.FEMALE)
				.departement("departement"+" "+6)
				.local_job_title("local_job_title"+" "+6)
				.type_de_contrat(TypeContrat.CDI)
				.supervisor("supervisor"+" "+6)
				.segment(Segment.TRANSPORTATION_SOLUTIONS)
				.code_site(CodeSite.N61)
				.position("position"+" "+6)
				.nom("nom"+" "+6)
				.prenom("prenom"+" "+6)
				.site(Site.TAC1_AUTO)
				.status("status"+" "+6)
				.build();

		List<Salarie> salaries=new ArrayList<>();
		salaries.add(salarie1);
		salaries.add(salarie2);
		salaries.add(salarie3);
		salaries.add(salarie4);
		salaries.add(salarie5);
		salaries.add(salarie6);
		salaries.add(salarie7);
		salaries.add(salarie8);
		salaries.add(salarie9);
		salaries.add(salarie10);
		salarieService.createSalaries(salaries);*/

		/*ApplicationUser user1=ApplicationUser.builder()
				.role(Role.ROLE_RH_PLUS1)
				.email("yasinino2015@gmail.com")
				.password(passwordEncoder.encode("123456"))
				.matricule("TE070550")
				.site(Site.TAC1_AUTO.name())
				.department("departement 1")
				.build();

		ApplicationUser user2=ApplicationUser.builder()
				.role(Role.ROLE_RH)
				.email("gharbilyouness43@gmail.com")
				.password(passwordEncoder.encode("123456"))
				.matricule("TE251844")
				.site(Site.TAC1_AUTO.name())
				.department("departement 1")
				.build();

		ApplicationUser user3=ApplicationUser.builder()
				.role(Role.ROLE_MANAGER)
				.email("spike43ultra@gmail.com")
				.password(passwordEncoder.encode("123456"))
				.matricule("TE718490")
				.site(Site.TAC1_AUTO.name())
				.department("departement 1")
				.build();

		ApplicationUser user4=ApplicationUser.builder()
				.role(Role.ROLE_SUPERVISEUR)
				.email("gharbil.youness@ensi.ma")
				.password(passwordEncoder.encode("123456"))
				.matricule("TE797691")
				.site(Site.TAC1_AUTO.name())
				.department("departement 1")
				.build();

		ApplicationUser user5=ApplicationUser.builder()
				.role(Role.ROLE_TEAM_LEADER)
				.email("yassine.amghar2220@gmail.com")
				.password(passwordEncoder.encode("123456"))
				.matricule("TE506227")
				.site(Site.TAC1_AUTO.name())
				.department("departement 1")
				.build();

		service.createUser(user1);
		service.createUser(user2);
		service.createUser(user3);
		service.createUser(user4);
		service.createUser(user5);


*/

	}
}
