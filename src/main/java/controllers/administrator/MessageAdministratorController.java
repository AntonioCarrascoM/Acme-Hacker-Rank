
package controllers.administrator;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;

@Controller
@RequestMapping("/message/administrator")
public class MessageAdministratorController extends AbstractController {

	//Services

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	//Create notification

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Message message;

		message = this.messageService.create();
		result = this.createEditModelAndView(message);

		return result;
	}

	//Broadcast notification

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "broadcast")
	public ModelAndView broadcast(Message message, final BindingResult binding) {
		ModelAndView result;

		try {
			message = this.messageService.reconstructBroadcast(message, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(message, "message.validation.error");
		} catch (final Throwable oops) {
			return this.createEditModelAndView(message, "message.commit.error");
		}

		try {
			this.messageService.broadcastMessage(message);
			result = new ModelAndView("redirect:/message/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/message/list.do");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String messageCode) {
		ModelAndView result;
		Collection<Actor> recipients;

		recipients = this.actorService.findAll();

		result = new ModelAndView("message/edit");
		result.addObject("msg", message);
		result.addObject("recipients", recipients);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "message/administrator/edit.do");

		return result;

	}
}
