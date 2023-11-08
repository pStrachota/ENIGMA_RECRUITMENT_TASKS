package pl.strachota.task2.util.annotations;

import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import pl.strachota.task2.model.Task;

@And({
        @Spec(path = "title", params = "title", spec = Like.class),
        @Spec(path = "description", params = "description", spec = Like.class),
        @Spec(path = "status", spec = EqualIgnoreCase.class),
})
public interface TaskSpec extends Specification<Task> {

}