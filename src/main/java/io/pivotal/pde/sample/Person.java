package io.pivotal.pde.sample;

import java.io.Serializable;
import java.util.Random;

import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.pdx.PdxInstance;
import com.gemstone.gemfire.pdx.PdxInstanceFactory;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

public class Person implements Serializable {


	private String lastName;
	private String firstName;
	private String phone;
	private Address address;
	private String gender;
	private Object id;
	private int age;
	
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}


	@Override
	public String toString() {
		return "Person [lastName=" + lastName + ", firstName=" + firstName + ", phone=" + phone + ", address=" + address
				+ ", gender=" + gender + ", id=" + id + ", age=" + age + "]";
	}


	private static Faker faker = new Faker();
	private static Random rand = new Random();

	public synchronized static PdxInstance fakePersonPdxInstance(int id, String zip){
		PdxInstanceFactory factory = ClientCacheFactory.getAnyInstance().createPdxInstanceFactory(Person.class.getName());
		
		Name fakeName = faker.name();
		
		factory.writeString("lastName", fakeName.lastName());
		factory.writeString("firstName", fakeName.firstName());
		factory.writeString("phone", faker.phoneNumber().phoneNumber());
		factory.writeObject("address", Address.fakeAddressPdxInstance(zip));
		
		if (rand.nextBoolean())
			factory.writeString("gender", "F");
		else
			factory.writeString("gender", "M");
		
		factory.writeInt("age",rand.nextInt(100));
		
		factory.writeInt("id", id);
		
		return factory.create();
	}

	
	public synchronized static Person fakePerson(){
		Person result = new Person();
		Name fakeName = faker.name();
		result.setLastName(fakeName.lastName());
		result.setFirstName(fakeName.firstName());
		result.setPhone(faker.phoneNumber().phoneNumber());
		result.setAddress(Address.fakeAddress());
		
		if (rand.nextBoolean())
			result.setGender("F");
		else
			result.setGender("M");
		
		result.setAge(rand.nextInt(100));
		
		return result;
	}

}
