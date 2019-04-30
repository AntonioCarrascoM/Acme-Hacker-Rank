
package controllers.actor;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	//Services

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Message> receivedMessages = this.messageService.receivedMessagesForActor(this.actorService.findByPrincipal().getId());
		final Collection<Message> sentMessages = this.messageService.sentMessagesForActor(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("message/list");
		result.addObject("receivedMessages", receivedMessages);
		result.addObject("sentMessages", sentMessages);
		result.addObject("requestURI", "message/list.do");

		return result;
	}

	//Displaying

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;
		final Message message = this.messageService.findOne(varId);
		final Integer principal = this.actorService.findByPrincipal().getId();

		//Assertion the actor listing these messages has the correct privilege
		if (principal.equals(message.getSender().getId()) || principal.equals(message.getRecipient().getId())) {
			result = new ModelAndView("message/display");
			result.addObject("msg", message);
			result.addObject("requestURI", "message/display.do");

			return result;

		}
		result = new ModelAndView("redirect:/welcome/index.do");
		return result;

	}

	//Creation

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {

		final ModelAndView result;
		Message message;

		message = this.messageService.create();
		result = this.createEditModelAndView(message);

		return result;
	}

	//Sending

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Message message, final BindingResult binding) {
		ModelAndView result;

		try {
			message = this.messageService.reconstruct(message, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(message);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(message, "message.commit.error");
		}

		try {

			final Message saved = this.messageService.save(message);
			this.messageService.send(saved, saved.getRecipient());
			result = new ModelAndView("redirect:/message/list.do");

		} catch (final Throwable oops) {

			result = this.createEditModelAndView(message, "message.commit.error");
		}

		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final Message message = this.messageService.findOne(varId);
		final Integer principal = this.actorService.findByPrincipal().getId();

		//Assertion the actor listing these messages has the correct privilege
		if (principal.equals(message.getSender().getId()) || principal.equals(message.getRecipient().getId())) {

			try {
				this.messageService.delete(message);
				result = new ModelAndView("redirect:/message/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error");
			}
			return result;
		}
		result = new ModelAndView("redirect:/welcome/index.do");
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String messageCode) {
		final ModelAndView result;

		final Collection<Actor> recipients = this.actorService.findAll();
		recipients.remove(this.actorService.findByPrincipal());

		result = new ModelAndView("message/edit");
		result.addObject("recipients", recipients);
		result.addObject("msg", message);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "message/edit.do");

		return result;

	}
}
