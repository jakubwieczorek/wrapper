package wieczorek.jakub.wrapper.configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by jakub on 07.07.17.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "wieczorek.jakub.wrapper")
public class WrapperConfiguration extends WebMvcConfigurerAdapter
{

}
