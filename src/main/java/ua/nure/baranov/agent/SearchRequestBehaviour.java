package ua.nure.baranov.agent;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;


/**
 * Behaviour of sending request to other agents to find user
 * @author Yevhenii Baranov
 *
 */
@SuppressWarnings("serial")
public class SearchRequestBehaviour extends Behaviour {

	private String lastName;
	private String firstName;
	private AID[] aids;

	@Override
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.setContent(firstName+","+lastName);
		for(AID aid:aids) {
			message.addReceiver(aid);
		}
		myAgent.send(message);
	}
	/**
	 * Creates behaviour that sends message to all agents in {@code aids} to find user with name {@code firstName} {@code lastName}.
	 * @param aids List of receivers
	 * @param firstName 
	 * @param lastName
	 */
	public SearchRequestBehaviour(AID[] aids, String firstName, String lastName) {
		this.aids = aids;
		this.firstName = firstName;
		this.lastName = lastName;
	
	}
	
	@Override
	public boolean done() {
		return true;
	}

}
