package com.ecommerce.customer.accounts.infrastructure.persistence.jpa.entities;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.type.SqlTypes.JSON;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.ecommerce.customer.accounts.infrastructure.persistence.jpa.valueobjects.OutboxStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(
		name = "outbox_events", 
		indexes = { 
				@Index(columnList = "status, created_at") 
		}
)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder
public final class OutboxEvent {
	@Id
	@GeneratedValue(strategy = UUID)
	UUID id;

	@Column(nullable = false)
	String aggregateType;

	@Column(nullable = false)
	String aggregateId;

	@Column(nullable = false)
	String eventType;

	@JdbcTypeCode(JSON)
	@Column(nullable = false)
	String payload;

	@Column(nullable = false)
	OffsetDateTime createdAt;

	@Builder.Default
	@Enumerated(STRING)
	@Column(nullable = false)
	OutboxStatus status = OutboxStatus.PENDING;
}
