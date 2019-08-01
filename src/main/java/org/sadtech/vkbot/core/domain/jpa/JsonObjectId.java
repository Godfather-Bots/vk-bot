package org.sadtech.vkbot.core.domain.jpa;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.sadtech.social.core.domain.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TODO: Добавить описание класса.
 *
 * @author upagge [28/07/2019]
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "event")
@NoArgsConstructor
public class JsonObjectId extends BasicEntity {

    @Column(name = "json", length = 500)
    private String json;

    public JsonObjectId(String json) {
        this.json = json;
    }
}
