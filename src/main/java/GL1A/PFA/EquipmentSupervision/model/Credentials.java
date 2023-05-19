package GL1A.PFA.EquipmentSupervision.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Credentials")
@Entity
public class Credentials implements UserDetails{
    @Id
    @Column(name = "user_id")
    private long user_id;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userid")
    private Utilisateur userid;

    @OneToMany(mappedBy = "credentials", cascade = CascadeType.DETACH)
    @JsonManagedReference
    @JsonIgnore
    private List<Authentification> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userid.getRole().name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
       return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
