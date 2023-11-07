package pl.strachota.task2.util.annotations;

import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import pl.strachota.task2.model.User;

@And({
        @Spec(path = "firstName", params = "firstName", spec = Like.class),
        @Spec(path = "lastName", params = "lastName", spec = Like.class),
        @Spec(path = "email", params = "email", spec = Like.class)
})
public interface UserSpec extends Specification<User> {

}