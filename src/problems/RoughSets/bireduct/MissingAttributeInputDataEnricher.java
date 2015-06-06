package problems.RoughSets.bireduct;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import evolver.RunEvolutionContext;
import output.OutputUtils;
import params.Parameters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * MissingAttributeInputDataEnricher.
 *
 * @author Maxim
 */
public class MissingAttributeInputDataEnricher implements InputDataEnricher {

    private StringBuilder changedValues = new StringBuilder();

    @Override
    public List<String> enrichData(List<String> lines) {
        List<String> result = new ArrayList<>(lines);
        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            if (containsMissingAttribute(line)) {
                String mostSimilar = findMostSimilarTo(line, lines);
                if (mostSimilar != null) {
                    String newLine = enrichLine(i, line, mostSimilar);
                    result.set(i, newLine);
                }
            }
        }
        return result;
    }

    private boolean containsMissingAttribute(String line) {
        List<String> attributes = Splitter.on(",").trimResults().splitToList(line);
        return attributes.indexOf(Parameters.getMissingAttributeSymbol()) != -1;
    }

    private String findMostSimilarTo(String line, List<String> lines) {
        List<String> lineAttrs = Splitter.on(",").trimResults().splitToList(line);
        int maxEqualsCount = -1;
        String mostSimilar = null;
        for (String otherLine : lines) {
            if (otherLine != line) {
                List<String> otherLineAttrs = Splitter.on(",").trimResults().splitToList(otherLine);
                int equalsCount = 0;
                for (int i = 0; i < lineAttrs.size(); ++i) {
                    String lineAttr = lineAttrs.get(i);
                    String otherAttr = otherLineAttrs.get(i);
                    if (lineAttr.equals(Parameters.getMissingAttributeSymbol()) && otherAttr.equals(Parameters.getMissingAttributeSymbol())){
                        equalsCount = -1;
                        break;
                    }
                    if (lineAttr.equals(otherAttr)) {
                        ++equalsCount;
                    }
                }
                if (equalsCount > maxEqualsCount) {
                    maxEqualsCount = equalsCount;
                    mostSimilar = otherLine;
                }
            }
        }
        return mostSimilar;
    }

    private String enrichLine(int row, String line, String mostSimilar) {
        List<String> lineAttrs = new ArrayList<>(Splitter.on(",").trimResults().splitToList(line));
        List<String> mostSimilarAttrs = Splitter.on(",").trimResults().splitToList(mostSimilar);
        for (int i = 0; i < lineAttrs.size(); ++i) {
            if (lineAttrs.get(i).equals(Parameters.getMissingAttributeSymbol())) {
                String newValue = mostSimilarAttrs.get(i);
                lineAttrs.set(i, newValue);
                changedValues.append(row).append(" ").append(i).append(" ").append(newValue).append("\n");
            }
        }
        return Joiner.on(",").join(lineAttrs);
    }

    public void writeMissingAttributesChanges(RunEvolutionContext evolutionContext) {
        String fileName = evolutionContext.getExtraOutputDir() + "missing_attribute.log";
        OutputUtils.mkdirsForPath(fileName);
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.write(changedValues.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}