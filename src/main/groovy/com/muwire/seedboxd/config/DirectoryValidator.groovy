package com.muwire.seedboxd.config

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class DirectoryValidator implements ConstraintValidator<WriteableDir, File>{

    @Override
    boolean isValid(File value, ConstraintValidatorContext context) {
        File file = value
        
        while(file != null && !file.exists())
            file = file.getParentFile()
        if (file == null)
            return false
        return file.isDirectory() && file.canWrite()
    }
}
