package party.threebody.skean.id.model;

import java.util.List;

public class User extends UserPO {
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    private List<Role> roles;
}
