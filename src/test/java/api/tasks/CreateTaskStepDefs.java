package api.tasks;

import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.Tasks;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;
import org.junit.Assert;

import java.util.List;

import static api.tasks.TasksServiceProvider.getTasksService;

public class CreateTaskStepDefs {

    TaskList list;

    @Given("^an empty task list$")
    public void anEmptyTaskList () throws Throwable {

        list = getTasksService().tasklists().insert(new TaskList().setTitle("New task list")).execute();

    }

    @When("^a user creates a task \"([^\"]*)\"$")
    public void a_user_creates_a_task (String taskTitle) throws Throwable {
        Task task = new Task();
        task.setTitle(taskTitle);
        Task result = getTasksService().tasks().insert(list.getId(), task).execute();
        System.out.println(result.getTitle());
    }

    @Then("^user sees tasks on the list$")
    public void user_sees_tasks_on_the_list (DataTable tasksTableExpected) throws Throwable {
        System.out.println();
        Tasks tasks = getTasksService().tasks().list(list.getId()).execute();

        List<Task> items = tasks.getItems();
        List<DataTableRow> gherkinRows = tasksTableExpected.getGherkinRows();

        Assert.assertEquals("There should be same amount of tasks", items.size(), gherkinRows.size());

        for (int i = 0; i < gherkinRows.size(); i++) {
            DataTableRow row = gherkinRows.get(i);
            String expectedTitle = row.getCells().get(0);
            Task task = items.get(i);
            String actualTitle = task.getTitle();
            Assert.assertEquals("Titles are the same", expectedTitle, actualTitle);
        }

    }
}
