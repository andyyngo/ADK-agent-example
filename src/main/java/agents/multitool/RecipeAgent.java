package agents.multitool;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.events.Event;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class RecipeAgent {

    private static String USER_ID = "student";
    private static String NAME = "multi_tool_agent";

    // The run your agent with Dev UI, the ROOT_AGENT should be a global public static variable.
    public static BaseAgent ROOT_AGENT = initAgent();

    public static BaseAgent initAgent() {
        return LlmAgent.builder()
            .name(NAME)
            .model("gemini-2.5-pro")
            .description("Agent to generate cooking recipes.")
            .instruction(
                """
                You are an agent that can provide detailed instructions a cooking recipes
                The answer should contains:
                1. serving size
                2. ingredients
                3. Cooking steps
                4. Nutrition information
                """)
            .tools(FunctionTool.create(RecipeAgent.class, "getRecipe"))
            .build();
    }

    public static Map<String, String> getRecipe(
        @Schema(description = "The name of a dish ie: ramen")
        String dishName) {
        if (dishName.equalsIgnoreCase("ramen")) {
            return Map.of(
                "status",
                "success",
                "General steps",
                "1. Make bone soup\n 2. Make aroma oil\n 3. Make pork chashu");

        } else {
            return Map.of(
                "status", "error", "report", "No instruction found for " + dishName);
        }
    }

    public static void main(String[] args) throws Exception {
        InMemoryRunner runner = new InMemoryRunner(ROOT_AGENT);

        Session session =
            runner
                .sessionService()
                .createSession(NAME, USER_ID)
                .blockingGet();

        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            while (true) {
                System.out.print("\nYou > ");
                String userInput = scanner.nextLine();

                if ("quit".equalsIgnoreCase(userInput)) {
                    break;
                }

                Content userMsg = Content.fromParts(Part.fromText(userInput));
                Flowable<Event> eventsFlowable = runner.runAsync(USER_ID, session.id(), userMsg);
                System.out.println("\nThinking...");
                Stream<Event> events = eventsFlowable.blockingStream();
                System.out.print("\nAgent > ");

                events.forEach(event ->
                        event.content()
                                .flatMap(Content::parts)
                                .filter(parts -> !parts.isEmpty())
                                .map(parts -> parts.get(0))
                                .flatMap(Part::functionResponse)
                                .ifPresent(functionResponse -> {
                                    String toolName = functionResponse.name().get();
                                    Object responseData = functionResponse.response().get();
                                    System.out.println("Tool '" + toolName + "' responded with: " + responseData);
                                })

                );
            }
        }
    }
}