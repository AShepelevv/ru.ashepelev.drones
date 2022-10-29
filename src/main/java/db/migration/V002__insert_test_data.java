package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.Math.abs;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.walk;
import static java.nio.file.Paths.get;
import static java.util.Objects.requireNonNull;
import static java.util.UUID.fromString;
import static org.apache.commons.io.FilenameUtils.getBaseName;
import static ru.ashepelev.drones.utils.RandomUtils.randomPositiveDouble;
import static ru.ashepelev.drones.utils.RandomUtils.randomLowerLetterString;

public class V002__insert_test_data extends BaseJavaMigration {
    private JdbcTemplate jdbcTemplate;

    public void migrate(Context context) throws IOException {
        jdbcTemplate = new JdbcTemplate(
                new SingleConnectionDataSource(context.getConnection(), true));

        // Create random medications
        walk(get(requireNonNull(getClass().getResource("image")).getPath()))
                .filter(Files::isRegularFile)
                .forEach(this::insertMedicationByImage);
    }

    private void insertMedicationByImage(Path f) {
        var imageId = fromString(getBaseName(f.getFileName().toString()));
        try {
            jdbcTemplate.update("insert into image values (?, ?)", imageId,
                    readAllBytes(f.toAbsolutePath()));
            var medicationName =  randomLowerLetterString(17);
            jdbcTemplate.update("insert into medication values (?, ?, ?, ?)",
                    medicationName.toUpperCase(), medicationName, randomPositiveDouble(100.0), imageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
