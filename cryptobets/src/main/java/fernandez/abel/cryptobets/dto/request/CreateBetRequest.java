package fernandez.abel.cryptobets.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
public class CreateBetRequest {

    @NotNull(message = "The user is required")
    @Size(min = 2, max = 50, message = "The length of user must be between 2 and 50")
    private String user;

    @NotNull(message = "The email is required")
    @Size(min=5, max = 50, message = "The length of mail must be between 2 and 50")
    @Email(message = "Email should be valid")
    private String mail;

    private String telegram;

    @NotNull(message = "The bet price is required")
    private Double betPrice;
}
