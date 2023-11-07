package pl.strachota.task2.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractEvent {

    private List<String> emails;
    private String title;

}
