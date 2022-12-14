package com.rhr.heat.entity;

import java.util.Objects;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Identity {
	protected UUID id;

	@Override
	public final int hashCode() {
		return Objects.hash(id);
	}
	
	public Boolean isSameAs(Identity identity) {
		return false;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Identity other = (Identity) obj;
		return Objects.equals(id, other.id);
	}
}
