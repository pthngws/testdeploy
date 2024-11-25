package com.group4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "owners")
@PrimaryKeyJoinColumn(name = "user_id") // Ánh xạ với cột user_id từ UserEntity
public class OwnerEntity extends UserEntity {

}
