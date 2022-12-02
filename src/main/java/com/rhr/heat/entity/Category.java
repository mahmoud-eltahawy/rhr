package com.rhr.heat.entity;

import java.util.ArrayList;
import java.util.List;

import com.rhr.heat.enums.Pushable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category{
    private String name;
    private Boolean hasMachines;
    private Boolean hasTemperature;

    public Category(String name) {
        this.name = name;
    }

    public List<Pushable> isPushable(){
        List<Pushable> result = new ArrayList<>();
        if(name == null){
            result.add(Pushable.CATEGORY_NUMBER_IS_NULL);
        }
        if(hasMachines == null){
            result.add(Pushable.CATEGORY_MACHINES_IS_NULL);
        }
        if(hasTemperature == null){
            result.add(Pushable.CATEGORY_TEMPERATURE_IS_NULL);
        }
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category other = (Category) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }    
}
