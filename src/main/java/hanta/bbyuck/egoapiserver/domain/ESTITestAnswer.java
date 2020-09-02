package hanta.bbyuck.egoapiserver.domain;

import hanta.bbyuck.egoapiserver.domain.enumset.ESTIVersion;
import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "esti_test_answer")
@SequenceGenerator(
        name = "esti_test_answer_seq_generator",
        sequenceName = "esti_data_sequence"
)
public class ESTITestAnswer {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "esti_test_answer_seq_generator"
    )
    @Column(name = "ESTI_TEST_ANSWER_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESPONDENT_ID")
    private User respondent;

    @Enumerated(EnumType.STRING)
    @Column(name = "version")
    private ESTIVersion version;

    @Column(name = "response_time")
    private LocalDateTime responseTime;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_1;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_2;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_3;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_4;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_5;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_6;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_7;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_8;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_9;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_10;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_11;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_12;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean answer_13;


    /*
     * 편의 메서드
     */

    public void fillAnswer(User respondent,
                           ESTIVersion version,
                           Boolean answer_1,
                           Boolean answer_2,
                           Boolean answer_3,
                           Boolean answer_4,
                           Boolean answer_5,
                           Boolean answer_6,
                           Boolean answer_7,
                           Boolean answer_8,
                           Boolean answer_9,
                           Boolean answer_10,
                           Boolean answer_11,
                           Boolean answer_12,
                           Boolean answer_13) {
        this.respondent = respondent;
        this.version = version;
        this.responseTime = LocalDateTime.now();
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.answer_4 = answer_4;
        this.answer_5 = answer_5;
        this.answer_6 = answer_6;
        this.answer_7 = answer_7;
        this.answer_8 = answer_8;
        this.answer_9 = answer_9;
        this.answer_10 = answer_10;
        this.answer_11 = answer_11;
        this.answer_12 = answer_12;
        this.answer_13 = answer_13;
    }
}
