package com.src.voucherPool.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="offers")
public class Offers extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id" , nullable=false)
	private Long Id;
	
	@Getter @Setter
	@OneToMany ( mappedBy="offers", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true )
	@JsonManagedReference
	private List<Vouchers> voucher = new ArrayList<>();
	
	@Getter @Setter
	@Column(name="SEVENT", nullable = true, updatable = true, insertable = true, length = 100)
	private String event;
	
	@Getter @Setter
	@Column(name="IDISCOUNT", nullable = true, updatable = true, insertable = true)
	private Integer discount;
	
	@Getter @Setter
	@Column(name="DEXPDT", nullable=true, updatable=true, insertable=true)
    private LocalDate expiryDate;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offers offer = (Offers) o;
        return event == offer.event &&
                Objects.equals(Id, offer.Id) &&
                Objects.equals(event, offer.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, event, discount);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Offers.class.getSimpleName() + "[", "]")
                .add("id=" + Id)
                .add("SEVENT='" + event + "'")
                .add("IDISCOUNT=" + discount)
                .add("DEXPDT=" + expiryDate)
                .toString();
    }
	
}
