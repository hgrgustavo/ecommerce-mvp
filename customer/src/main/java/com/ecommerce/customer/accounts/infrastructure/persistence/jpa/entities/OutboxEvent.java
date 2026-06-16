package com.ecommerce.customer.accounts.infrastructure.persistence.jpa.entities;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.type.SqlTypes.JSON;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.ecommerce.customer.accounts.infrastructure.persistence.valueobjects.OutboxStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
		name = "outbox_events", 
		indexes = { 
				@Index(columnList = "status, created_at") 
		}
)
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public final class OutboxEvent {
	@Id
	@GeneratedValue(strategy = UUID)
	private UUID id;

	@Column(nullable = false)
	private String aggregateType;

	@Column(nullable = false)
	private String aggregateId;

	@Column(nullable = false)
	private String eventType;

	@JdbcTypeCode(JSON)
	@Column(nullable = false)
	private String payload;

	@Column(nullable = false)
	private OffsetDateTime createdAt;

	@Builder.Default
	@Enumerated(STRING)
	@Column(nullable = false)
	private OutboxStatus status = OutboxStatus.PENDING;
}
