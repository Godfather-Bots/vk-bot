package org.sadtech.vk.bot.sdk.domain.jpa;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.sadtech.social.core.domain.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author upagge [28/07/2019]
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "event")
@NoArgsConstructor
public class JsonObjectId extends BasicEntity {

    @Column(name = "json", length = 3000)
    private String json;

    public JsonObjectId(String json) {
        this.json = json;
    }

}
