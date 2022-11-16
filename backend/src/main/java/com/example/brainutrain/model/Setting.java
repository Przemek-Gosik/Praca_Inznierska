package com.example.brainutrain.model;

import com.example.brainutrain.constants.FontSize;
import com.example.brainutrain.constants.Theme;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name="settings")
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSetting;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FontSize fontSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Theme theme;

    @NotNull
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Setting(FontSize fontSize, Theme theme,User user) {
        this.fontSize = fontSize;
        this.theme = theme;
        this.user = user;
    }

}
