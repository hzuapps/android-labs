package edu.hzuapps.androidworks.homeworks2;

public class Net1314080903124_Digital {
	public int id;

	public Net1314080903124_Digital(int id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "Net1314080903124_Digital [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Net1314080903124_Digital other = (Net1314080903124_Digital) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}
