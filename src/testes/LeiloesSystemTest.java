package testes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LeiloesSystemTest {
	
	private WebDriver driver;
	private LeiloesPage leiloes;
	
	@Before
	public void inicializa(){ 
		System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
		this.driver = new ChromeDriver();
		leiloes = new LeiloesPage(driver);
		driver.manage().window().maximize();
		
		UsuariosPage usuarios = new UsuariosPage(driver);
		usuarios.visita();
		if (usuarios.existeNaListagem("teste fulano", "fulano@gmail.com") == false){
			usuarios.novo().cadastro("teste fulano", "fulano@gmail.com");
		}
	}
	@Test
	public void deveCadastrarUmLeilao(){
		leiloes.visita();
		NovoLeilaoPage novoLeilao = leiloes.novo();
		novoLeilao.preenche("geladeira", 45, "teste fulano", true);
		assertTrue(leiloes.existe("geladeira", 45, "teste fulano", true));
	}
	@Test
	public void naoDeveCadastrarUmLeilaoSemNome(){
		leiloes.visita();
		NovoLeilaoPage novoLeilao = leiloes.novo();
		novoLeilao.preenche("", 45, "teste fulano", true);
		assertTrue(novoLeilao.validaNomeObrigatorio());
	}
	@Test
	public void naoDeveCadastrarUmLeilaoSemValor(){
		leiloes.visita();
		NovoLeilaoPage novoLeilao = leiloes.novo();
		novoLeilao.preenche("semValor", 0, "teste fulano", true);
		assertTrue(novoLeilao.validaValorObrigatorio());
	}
	@After
    public void finaliza() {
		driver.close();
    }
}
