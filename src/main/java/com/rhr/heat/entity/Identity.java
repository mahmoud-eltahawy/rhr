package com.rhr.heat.entity;

import java.util.Objects;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class Identity {
	protected final UUID id;

	@Override
	public final int hashCode() {
		return Objects.hash(id);
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
