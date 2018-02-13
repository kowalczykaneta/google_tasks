package api.tasks;

import com.google.api.client.util.Collections2;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.Tasks;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;
import org.junit.Assert;

import java.util.Collections;
import java.util.List;

import static api.tasks.TasksServiceProvider.getTasksService;

public class MoveTaskStepDefs {

    TaskList list;

    @Given("^a task list with three tasks$")
    public void a_task_list_with_three_tasks (List<String> taskTitles) throws Throwable {
        list = getTasksService().tasklists().insert(new TaskList().setTitle("Tasks to move")).execute();

        for (int i = taskTitles.size() - 1; i >= 0; i--) {
            String taskTitle = taskTitles.get(i);

            Task task = new Task();
            task.setTitle(taskTitle);
            getTasksService().tasks().insert(list.getId(), task).execute();

        }
    }

    @When("^a user moves \"([^\"]*)\" to the last position$")
    public void a_user_moves_to_the_last_position (String arg1) throws Throwable {

        Tasks tasks = getTasksService().tasks().list(list.getId()).execute();

        List<Task> items = tasks.getItems();


        Task taskOne = null;
        Task taskThree = null;

        for (Task task : items){

           if (task.getTitle().equals("Task one")){
               taskOne = task;
           }

            if (task.getTitle().equals("Task three")){
               taskThree = task;
            }
        }

        getTasksService().tasks()
            .move(list.getId(), taskOne.getId())
            .setPrevious(taskThree.getId())
            .execute();

    }

    @Then("^user sees tasks in order$")
    public void user_sees_tasks_in_order (List<String> arg1) throws Throwable {

        Tasks tasks = getTasksService().tasks().list(list.getId()).execute();
        System.out.println(tasks);

    }

}

