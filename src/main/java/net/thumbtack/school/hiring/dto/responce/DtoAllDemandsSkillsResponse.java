// net.thumbtack.school.hiring.dto.response;
package net.thumbtack.school.hiring.dto.responce;

import java.util.Set;

public class DtoAllDemandsSkillsResponse {
    private Set<String> allDemandsSkills;

    public DtoAllDemandsSkillsResponse(Set<String> allDemandsSkills) {
        this.allDemandsSkills = allDemandsSkills;
    }

    public Set<String> getAllDemandsSkills() {
        return allDemandsSkills;
    }

    public void setAllDemandsSkills(Set<String> allDemandsSkills) {
        this.allDemandsSkills = allDemandsSkills;
    }
}
